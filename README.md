# What is it?

This is a project for Java intensive course.

# Application Commands

This application provides various commands to manage clients and apartments. Below is a list of available commands along with their syntax and descriptions:

## Client Commands

### Create Client

```
create client
```

Creates a *new client* with the **automatically generated** UUID and name.

### Create Specified Client

```
create client UUID name
```

Creates a *new client* with the **specified** UUID and name.

### Delete Client

```
delete client UUID
```

*Deletes* the client with the **specified** UUID.

### Get Client

```
get client UUID
```

Retrieves and displays the *details of the client* with the **specified** UUID.

### Get All Clients

```
get_all client sort
```

***'sort'*** param have to contain [ ID NAME STATUS ]

Retrieves and displays *list of all clients* with **specified** sort param.

### Check-in Client

```
check_in clientUUID apartmentUUID
```

Associates the client with the specified UUID with the apartment with the specified UUID.

### Check-out Client

```
check_out clientUUID apartmentUUID
```

Disassociates the client with the specified UUID from the apartment with the specified UUID.

### Calculate Client Stay Price

```
calculate_price clientUUID
```

Calculates and displays the price for the stay of the client with the specified UUID.

## Apartment Commands

### Create Apartment

```
create apartment
```

Creates a *new apartment* with the **automatically** generated UUID, price, capacity, availability, and apartment status.

### Create Specified Apartment

```
create apartment [UUID price capacity availability apartment_status]
```

Creates a *new apartment* with the **specified** UUID, price, capacity, availability, and apartment status.

### Delete Apartment

```
delete apartment UUID
```

*Deletes* the apartment with the **specified** UUID.

### Get Apartment

```
get apartment UUID
```

Retrieves and displays the *details of the apartment* with the **specified** UUID.

### Get All Apartments

```
get_all apartment sort
```

***'sort'*** param have to contain [ ID CAPACITY AVAILABILITY PRICE ]

Retrieves and displays *list of all apartments* with **specified** sort param.

### Adjust Apartment Price

```
adjust apartmentUUID price
```

*Adjusts the price* of the apartment with the **specified** UUID to the specified price.

## Exit

```
exit
```

*Exits the application.*
