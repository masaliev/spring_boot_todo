FROM kkarczmarczyk/node-yarn:latest
RUN mkdir -p /usr/src/app
ADD src /usr/src/app/src
ADD package.json /usr/src/app/package.json
ADD webpack.config.prod.js /usr/src/app/webpack.config.prod.js
WORKDIR /usr/src/app