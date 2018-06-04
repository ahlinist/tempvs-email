# tempvs-email
[![Circle CI](https://circleci.com/gh/ahlinist/tempvs-email/tree/master.svg?&style=shield)](https://circleci.com/gh/ahlinist/tempvs-email/tree/master)

An email microservice for tempvs (see: https://github.com/ahlinist/tempvs)
 
### SMTP configuration
SMTP-connection parameters are retrieved from env variables:
 * SMTP_HOST
 * SMTP_PORT
 * SMTP_USERNAME
 * SMTP_PASSWORD
 * TOKEN (security token that matches the one being set up in the host app)

## Running installations
### Stage
http://stage.email.tempvs.club
### Prod
http://email.tempvs.club
