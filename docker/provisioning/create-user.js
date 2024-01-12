db.createUser({
    "user": "reservation_local",
    "pwd": "reservation_local",
    "roles": [
        {
            "role": "readWrite",
            "db": "reservation_local"
        }
    ]
});
