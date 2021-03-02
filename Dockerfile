FROM node
RUN yum install docker-ce docker-ce-cli containerd.io
RUN systemctl start docker
WORKDIR /usr/src/nodeapp
Run npm install -g npm@7.6.0
COPY . /usr/src/nodeapp
USER jenkins
CMD ["npm","start"]

