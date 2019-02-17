# Competitive Programming

## ELK

### Init

```
mkdir D:\tmp\elk-data
mkdir D:\var\log
touch D:\var\log\competitive-programming.log
```

Run ELK with Docker : `docker-compose up elk`

Go to [Kibana](http://localhost:5601)

Run `com.mbouchenoire.competitive.programming.hashcode.qualification2018.Application`

Check `D:\var\log\competitive-programming.log` is not empty.

Refresh [Kibana](http://localhost:5601)

Define index pattern `%{[@metadata][beat]}-*` with time Filter field name `@timestamp`

### Filter
`input:a_example.in && vehicule-index:0`

Custom index are :
* input
* vehicule-index
* ride-index