# SISTEME DISTRIBUITE - ASSIGNMENT 1  
## Request-Reply Communication  

DS2025_30641_Barna_Melisa

---

## Descriere Generala

Proiectul implementeaza un Sistem de Management al Energiei (Energy Management System - EMS) bazat pe microservicii containerizate.  
Sistemul permite utilizatorilor autentificati sa acceseze si sa gestioneze dispozitive de masurare a energiei, cu restrictii bazate pe roluri (Admin si Client).

---

## 1. Componente ale Solutiei

Solutia este construita folosind o arhitectura de microservicii, orchestrata prin Docker.  
Principalele componente sunt urmatoarele:

- Frontend: Interfata web pentru utilizatori (Login, vizualizare in functie de rol, CRUD). Poate fi realizata in ReactJS sau Angular.  
- API Gateway / Reverse Proxy: Punctul de intrare unic in sistem. Gestioneaza rutarea, autentificarea si autorizarea (validarea token-ului JWT). Poate fi implementat cu Traefik sau Spring Cloud Gateway.  
- Authorization Service: Se ocupa de login, inregistrare, gestionarea credentialelor si generarea token-ului JWT. Implementat in Java Spring REST.  
- User Management Microservice: Realizeaza operatiile CRUD pe conturile de utilizatori (doar pentru Admin). Implementat in Java Spring REST.  
- Device Management Microservice: Permite CRUD pe dispozitive si asocierea acestora la utilizatori. Implementat in Java Spring REST.

---

## 2. Rularea Proiectului (Docker Deployment)

Proiectul este conceput pentru a fi rulat integral folosind Docker Compose, astfel incat toate microserviciile si dependintele lor (bazele de date) sa porneasca in ordinea corecta.

### 2.1. Cerinte preliminare

Asigurati-va ca aveti instalate urmatoarele aplicatii:
- Docker Engine  
- Docker Compose  
- Git  

### 2.2. Construirea si rularea serviciilor

1. Navigati in directorul radacina al proiectului, acolo unde se afla fisierul `docker-compose.yml`.

2. Construirea imaginilor Docker:  
   Aceasta comanda citeste Dockerfile-urile din fiecare subdirector si construieste imaginile pentru toate serviciile (Frontend, Backend si Gateway).

   ```bash
   docker compose build
   ```
   
### 2.3 Rularea containerelor:
 1. Comanda urmatoare porneste toate containerele in modul detached (in fundal).

```docker compose up -d```

### 2.4 Accesarea aplicatiei:
 1. Dupa ce toate serviciile pornesc (poate dura cateva zeci de secunde), aplicatia frontend poate fi accesata in browser la adresa:

```http://localhost:[PORTUL_FRONTEND]```

### 2.5 Oprirea si eliminarea serviciilor

 1. Pentru a opri si elimina toate containerele, retelele si volumele create de Docker Compose, folositi comanda:

```docker compose down```

### 3. Functionalitati Principale

 Rolurile definite in sistem sunt Administrator si Client, fiecare avand drepturi specifice.

1. Administrator

-Poate face operatii CRUD pe conturile de utilizatori:
 -GET /users
 -POST /users

-Poate face operatii CRUD pe dispozitive:
-GET /devices
-POST /devices
-PUT /devices/{id}

-Poate asocia un dispozitiv unui utilizator:
 -PUT /devices/{id} (cu ownerId)

2. Client

-Poate face login (autentificare) prin:
 -POST /auth/login

-Poate vizualiza dispozitivele asociate contului sau:
 -GET /devices/mine
 -GET /devices?ownerId={UUID}


