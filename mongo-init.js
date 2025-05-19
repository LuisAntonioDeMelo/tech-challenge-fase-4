db = db.getSiblingDB('tech-challenge-cliente'); 

db.createUser({
  user: 'techchallenge',
  pwd: 'techchallenge123',
  roles: [
    {
      role: 'readWrite',
      db: 'tech-challenge-cliente'
    }
  ]
});