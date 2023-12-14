<h1>Crud Básico</h1>

> Neste projeto prático tento aprofundar nós reais conceitos de um CRUD , em quesito dos métodos de requisição HTTP mais usados no dia a dia,
> abordando o GET,POST,PUT,PATCH e DELETE bem simples e prático em uma API em <https://spring.io/guides/gs/spring-boot/> com Java .

<h3>Camada de Service do padrão MVC</h3>
<p>A camadda de Service é utilizada para conter a regra de negocio, serve também como uma interface entre o Controller e o Model 
  deixando assim a regra de negocio mais limpa e modular , deixando cada método bem mais legivel e facil de se entender deixando bem nitido o <code>Single Responsiblity Principle</code> </p>


  ~~~java
    @Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        var userSave = repository.save(user);
        return userSave;
    }
~~~
* A injeção de dependência com a anotação <code>@Autowired</code> é um recurso do Spring onde facilita muito reduzindo o acoplamento entre as classes.
* Já no metódo save , que passamos um **User** como retorno que é uma class na minha camada <code>Model</code> que usamos o metódo da camada <code>UserRepository</code> onde essa camada é responsavel pela persistência de dados , ela possui seus próprios metódos para realizar leitura,criar,atualização e deleção **(CRUD)**.
* No exemplo acima podemos ver um dos métodos **repository.save();** esse método é reponsável por criar a entidade e persistir os dados .

<h4>Métodos de Leitura :</h4>

~~~java 
 public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findByID(Long id) {
        return repository.findById(id);
    }
~~~
* Nesses dois métodos utilizamos para fazer a letura(busca) por entidades no meu Banco de Dados.
* No primeiro método buscamos todos os User em uma estrutura List que irá trazer toda entidade no nosso banco pois o <code>repository.findAll</code> retornará todas as entidades que foram persistidas.
* Já no segundo método a busca é pelo **ID** do **User** uqe é passado como parâmetro , por isso usamos um <code>Optional</code> porque há a possibilidade de informar um **ID** que não existe, se o **ID** existir será exibido o **User** daquele respectivo **ID**.

<h4>Métodos de Atualização</h4>

~~~java
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
~~~
* Esse método que precisa usar uma lógica para realizar uma atualização é bem simples, o método já é autoexplicativo PutUser que irei mostrar logo após que é um dos metódos HTTP .
* Nesse método atualizamos todos os atributos da entidade , sutilmente enxergamos outra estrutura de dados que sé é passada nos parâmetros que é o <code>Map</code> (chave : valor ) o **ID** sendo a chave e **User** sendo o valor que irá ser alterado ao final do metodo.

  ~~~java
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
* O método <code>patchUser</code> ao contrario do Put que atualiza todos os atributos da entidade o <code>patchUser</code> atualiza somente o atributo especifico que precisa da atualização , por isso é muito importante observar os atributos que irá colocar na sua lógica pois nem todos podem ser atualizado para mantermos uma integridade da entidade.
* Método muito importante mas pouco utilizado , principalmente por pessoas que estão aprendendo CRUD uma sigla tão importante e tão usada no dia a dia de um programador . Mais importante do que fazer é realmente saber porque está fazendo








~~~
