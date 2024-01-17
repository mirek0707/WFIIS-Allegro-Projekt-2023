db.createUser({
    "user": "user_local",
    "pwd": "user_local",
    "roles": [
        {
            "role": "readWrite",
            "db": "user_local"
        }
    ]
});

db = new Mongo().getDB("user_local");

db.createCollection('roles', {capped: false});

db.roles.insertMany([
    { name: "ROLE_USER" },
    { name: "ROLE_PREMIUM" },
    { name: "ROLE_ADMIN" },
]);
