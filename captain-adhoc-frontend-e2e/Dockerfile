FROM node:12.0.0

WORKDIR /opt/test/e2e

VOLUME ./target

COPY package.json nightwatch.json wait-for-it.sh ./
COPY test ./test

RUN chmod +x wait-for-it.sh && npm install
