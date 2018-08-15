# tempvs-email
[![Circle CI](https://circleci.com/gh/ahlinist/tempvs-email/tree/master.svg?&style=shield)](https://circleci.com/gh/ahlinist/tempvs-email/tree/master)

An email microservice for tempvs (see: https://github.com/ahlinist/tempvs). Based on tempvs-rest client (see: https://github.com/ahlinist/tempvs-rest).
 
## Configuration
SendGrid lib is used for email deliveries. The following env variables need to be set correctly:
 * SENDGRID_API_KEY
 * PORT (8080 - default)
 * TOKEN (security token that matches the one being set up in the host app)

## Running installations
### Stage
http://stage.email.tempvs.club
### Prod
http://email.tempvs.club
