# tempvs-email
[![Circle CI](https://circleci.com/gh/ahlinist/tempvs-email/tree/master.svg?&style=shield)](https://circleci.com/gh/ahlinist/tempvs-email/tree/master)

A microservice component responsible for email deliveries for tempvs (see: https://github.com/ahlinist/tempvs).

## Configuration
SendGrid lib is used for email deliveries. The following env variables need to be set correctly:
 * SENDGRID_API_KEY
 * PORT
 * TOKEN (security token that matches the one being set up in the host app)
