FROM node:liatest
WORKDIR /usr/src/nodeapp
Run npm install -g npm@7.6.0
COPY . /usr/src/nodeapp

CMD ["npm","start"]
