FROM node
RUN yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
RUN yum install docker
RUN systemctl start docker
WORKDIR /usr/src/nodeapp
Run npm install -g npm@7.6.0
COPY . /usr/src/nodeapp
USER jenkins
CMD ["npm","start"]

