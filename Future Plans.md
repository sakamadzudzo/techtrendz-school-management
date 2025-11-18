# Future Plans

## Dashboards
Make better dashboards. Maybe find a designer to help...

## Improvements
- Implement profile photo...
- If user has multiple roles, ask which role to activate and save. User to have option to change active role without logout.
- ***Plan***: Which areas can be optimized and how would you do it (Frontend & Backend)?
- ***Plan***: How and where to add caching (backend)?
- ***Plan***: How and where to make data-changing transactional (backend)?
- Add ID number to `Person` object. Propagate to frontend
- Add `@NotNull` to all fields with `@Column(nullable = false)`

## Minor
- reports page -> filter which to show by role
- When role is parent, only show home and parent/one.
- Label for parent/one -> View Info
- **Check logic of progress reports**
- Copy `getOrderSpecifiers` from `BankQueryService`
- Copy `GenericService` and impl from `BankService` and impl logic
- Pass `new String[0]` to `toArray` method calls
- *Use jlink or other tools to create standalone application*

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

#### *Points to remember for finance module*
- ??? When user saves payment record, check table of references if such reference exists. If it is claimed, raise error, else claim it and mark payment as successful ???
- ??? When list of references are imported, skip all recorded references. Also run function to verify all recorded yet unverified payments ???
- ??? On uploading payment references, can be JSON, CSV, or excel ???
- ??? Create and download templates for each, using endpoint that accepts enum UploadType ???
- ??? Upload endpoint accepts file and enum UploadType, used by service to determine decoding function to call (decoding function reads file to `List<PaymentReference>`)???



## Images
Make endpoint to receive image and person id
Crop/edit image to standard size
Save on disk and save imageUrl to person

Make endpoint to retrieve image using imageUrl

Maybe have avatarUrl and separate imagesUrls list in person

?Should person have more than one picture?

## Tests
- Besides unit tests, what other tests can I run?
1. Unit
2. Integration
3. HTTP
4. Report aggregations
5. Entity to DTO
6. DTO structure
- Group the possible tests into actionable chuncks.
- Looking at the current tests, what services do not yet have tests?