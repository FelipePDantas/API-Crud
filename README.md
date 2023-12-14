<h1>Crud Básico</h1>
>Neste projeto prático tento aprofundar nós reais conceitos de um CRUD , em quesito dos métodos de requisição HTTP mais usados no dia a dia,
>abordando o GET,POST,PUT,PATCH e DELETE bem simples e prático em uma API em [Spring Boot](https://spring.io/guides/gs/spring-boot/) com Java .

<h3>Camada de Service do padrão MVC</h3>
<p>A camadda de Service é utilizada para conter a regra de negocio, serve também como uma interface entre o Controller e o Model 
  deixando assim a regra de negocio mais limpa e modular , deixando cada método bem mais legivel e facil de se entender deixando bem nitido o Single Responsiblity Principle  </p>


  ~~~java
    @Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        var userSave = repository.save(user);
        return userSave;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findByID(Long id) {
        return repository.findById(id);
    }

    public User putUser(Long id, User userUpdate) {

        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User updtate = userOptional.get();

            updtate.setName(userUpdate.getName());
            updtate.setEmail(userUpdate.getEmail());
            updtate.setContact(userUpdate.getContact());

            User updateUser = repository.save(updtate);
            return updateUser;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found");
        }
    }

    public User patchUser(Long id, User userDetails) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            User updateEspecific = optionalUser.get();

            if (userDetails.getName() != null) {
                updateEspecific.setName(userDetails.getName());
            }
            if (userDetails.getEmail() != null) {
                updateEspecific.setEmail(userDetails.getEmail());
            }
            if (userDetails.getContact() != null) {
                updateEspecific.setContact(userDetails.getContact());
            }
            updateEspecific = repository.save(updateEspecific);
            return updateEspecific;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found");
    }

    public void deleteId(Long id) {
        repository.deleteById(id);
    }
~~~
