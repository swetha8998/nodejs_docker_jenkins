FROM node
RUN RUN curl -fsSLO https://get.docker/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz
WORKDIR /usr/src/nodeapp
Run npm install -g npm@7.6.0
COPY . /usr/src/nodeapp
USER jenkins
CMD ["npm","start"]

