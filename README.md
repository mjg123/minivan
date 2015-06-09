# minivan

Minimum Viable Nova

## Wait, what!? ...why?

OpenStack is, roughly-speaking, made up of several different services,
each of which talks to the others through more-or-less well defined
HTTP APIs. So, in theory it is possible to replace any component with
another which provides the same API.

But, there's a problem with this - that the APIs are not very well
defined. The services, and their APIs, are defined by the
implementations of each one. So far as I know, there is not a single
case of an API-compatible choice for any of the OpenStack services.  In
my opinion, this is a failure in a so-called service-oriented
architecture, because what we really have is a monolithic application
with an extremely complicated deployment model.

Minivan attempts to be an API-compatible drop-in replacement for nova.
It shares no internal architecture - it's written in lisp for a start.
My main sources of information about how minivan should behave are
nova's API documents and (sadly necessary) debugging the HTTP chat
between nova and its clients.  It is written as a learning exercise:
learning about OpenStack, about hypervisors, about all kinds of things.
And hopefully will lead towards implementations of services in
OpenStack being defined by their APIs rather than the opposite.

## Getting started

### Get everything together

Download the source by cloning this repo.

Build minivan using [leiningen](http://leiningen.org) and running `lein ring uberjar`.

Take the generated file `target/minivan-VERSION-standalone.jar` onto a box which you can run devstack on.

Clone devstack from https://github.com/openstack-dev/devstack .

```
sudo apt-get install openjdk-7-jre-headless
export PORT=4000
export URL=http://10.10.10.10:$PORT   # substitute your IP address
java -jar minivan-VERSION-standalone.jar
curl $URL
```

You should see `Hello from minivan!` so you know it's running.

### Start up OpenStack

`export ENABLED_SERVICES=key,g-api,g-reg,c-sch,c-api,c-vol,horizon,rabbit,tempest,mysql,dstat`

`stack.sh` to bring up a nova-less OpenStack.

### Add minivan to your OpenStack cloud

```
openstack service create \
  --name nova --description "Not really nova" compute

openstack endpoint create \
  --publicurl   $URL      \
  --internalurl $URL      \
  --adminurl    $URL      \
  --region RegionOne compute
```

### ??? Profit

```
$ nova flavor-list
+-----+-------+-----------+------+-----------+------+-------+-------------+-----------+
| ID  | Name  | Memory_MB | Disk | Ephemeral | Swap | VCPUs | RXTX_Factor | Is_Public |
+-----+-------+-----------+------+-----------+------+-------+-------------+-----------+
| 100 | small | 1024      | 10   | N/A       | 0    | 1     | 1           | N/A       |
| 200 | big   | 4096      | 20   | N/A       | 0    | 2     | 1           | N/A       |
+-----+-------+-----------+------+-----------+------+-------+-------------+-----------+
```

LOL something worked!


## License

Copyright © 2015 Matthew Gilliard

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
