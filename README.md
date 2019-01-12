# tempvs-email
[![Circle CI](https://circleci.com/gh/ahlinist/tempvs-email/tree/master.svg?&style=shield)](https://circleci.com/gh/ahlinist/tempvs-email/tree/master)

A microservice component responsible for email deliveries for tempvs (see: https://github.com/ahlinist/tempvs).

## Discovery
To be discovered by tempvs services this component should have the following env variables set correctly:
 * DOMAIN_NAME (domain name specific for this group of instances, defaults to "localhost")
 * EUREKA_URL (Eureka server url, defaults to "http://user:password@localhost:8084")
 * EUREKA_PASSWORD (Eureka server password, defaults to "password")

## Configuration
SendGrid lib is used for email deliveries. The following env variables need to be set correctly:
 * SENDGRID_API_KEY
 * PORT
 * TOKEN (security token that matches the one being set up in the host app)
