# Future Plans

## Dashboards
Make better dashboards. Maybe find a designer to help...

## Improvements
- Implement profile photo...
- If user has multiple roles, ask which role to activate and save. User to have option to change active role without logout.
- *Plan*: Which areas can be optimized and how would you do it (Frontend & Backend)?
- *Plan*: How and where to add caching (backend)?
- *Plan*: How and where to make data-changing transactional (backend)?

## Ambitious
- Convert to microservices with:
- Auth
- School
- Payment
- Can add more...

## Financial/Accounts module
### Fee
- Student id
- Year
- Amount (class + dorm + EA)

### FeePayment
- Fee id
- Amount
- Payment type (cash, swipe, mobile money, Zipit, kind)
- Bank id
- Reference type (e.g. transaction id, ref etc)
- Reference 
- Paying account (in case of not cash)
- Kind type (only show if in kind)
- Currency
- Payment date
- Receiving account (Account)

### Bank
- id
- name
- description 
- hq address 

### Account 
- id
- name
- description 
- holder (person)
- bank
- branch
- branch code
- account number
- currency



## Images
Make endpoint to receive image and person id
Crop/edit image to standard size
Save on disk and save imageUrl to person

Make endpoint to retrieve image using imageUrl

Maybe have avatarUrl and separate imagesUrls list in person

?Should person have more than one picture?