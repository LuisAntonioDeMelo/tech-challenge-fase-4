db = db.getSiblingDB('admin');
db.auth('root', 'example');

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

// Criar a coleção cliente para garantir que o banco de dados seja criado
db.createCollection('cliente');