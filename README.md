# Cake App

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

**Table of Contents**

- [Development Environment Setup](#development-environment-setup)
  - [Setup Frontend](#setup-frontend)
    - [Prerequisites](#prerequisites)
    - [Running the frontend](#running-the-frontend)
    - [Running the unit and integration tests](#running-the-unit-and-integration-tests)
  - [Setup Backend](#setup-backend)
      - [Running the backend](#running-the-backend)
    - [For backend developers](#for-backend-developers)
      - [Prerequisites](#prerequisites)
      - [Running the backend](#running-the-backend)
      - [Running the backend Tests](#running-the-backend-tests)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Development Environment Setup

### Setup Frontend

#### Prerequisites

- Docker >= 19.03 - https://docs.docker.com/docker-for-mac/install/
- Node >= 12.6.0 - https://github.com/creationix/nvm
- Yarn >= 1.22.5 - https://yarnpkg.com/en/docs/install

#### Running the frontend

You can run the frontend by running `yarn start` from within the `/frontend` directory.\
The frontend should then be served at http://localhost:3000

#### Running the unit and integration tests

**Note:** Jest uses a package called 'watchman' that is currently not working for macbooks running Sierra. Current work around is to brew install the package manually - `brew install watchman`

`yarn test` - Runs the unit tests (Jest)
`yarn run test-coverage` - Runs the unit tests with coverage


### Setup Backend

#### For backend developers

###### Prerequisites

- Docker >= 19.03 - https://docs.docker.com/docker-for-mac/install/
- Java >= 11 - https://sdkman.io
- Gradle >= 5.6.4 - https://sdkman.io

##### Running the backend

It's best to run the backend from within IntelliJ as a Spring Boot application.\
You will need to set the following in the IntelliJ Run Configuration:

- Active profiles: `dev`

You will also need to run the database containers. These can be run with the following commands:

- `docker-compose up db`

##### Running the backend Tests

You can either run the below commands from within the backend directory or run the tests with IntelliJ.

`gradle flywayClean` - Removes all the data in the database.\
`gradle flywayMigrate` - Runs migration scripts to restore data into the database.\
`gradle test` - Runs both unit and integration tests.\
`gradle unitTest` - Runs unit tests (tests with the annotation `@Category(UnitTest.class)`).\
`gradle integrationTest` - Runs integration tests (tests with the annotation `@Category(IntegrationTest.class)`).

