<h1>Crud Básico</h1>

> Neste projeto prático tento aprofundar nós reais conceitos de um CRUD , em quesito dos métodos de requisição HTTP mais usados no dia a dia,
> abordando o GET,POST,PUT,PATCH e DELETE bem simples e prático em uma API em <https://spring.io/guides/gs/spring-boot/> com Java .

<h3><strong>Camada de Service do padrão MVC</strong></h3>
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
  ~~~
* O método <code>patchUser</code> ao contrario do Put que atualiza todos os atributos da entidade o <code>patchUser</code> atualiza somente o atributo especifico que precisa da atualização , por isso é muito importante observar os atributos que irá colocar na sua lógica pois nem todos podem ser atualizado para mantermos uma integridade da entidade.
* Método muito importante mas pouco utilizado , principalmente por pessoas que estão aprendendo CRUD uma sigla tão importante e tão usada no dia a dia de um programador . Mais importante do que fazer é realmente saber porque está fazendo
* Ao final dos dois métodos se o usuário não estiver cadastrado irá retornar um <code>ResponseStatusException</code> passando o **NOT_FOUND** que no **Controller** irá retornar o status 404 que é recorrente por não encontrar o dado que foi passado .


<h4>Método de deletar</h4>

~~~java

  public void deleteId(Long id) {
        repository.deleteById(id);
    }
~~~
* O método de deleção é bem simples , não há retorno pois irá só deletar um registro no Banco de dados segundo o id que é passado no parâmetro.

<h3><strong>Camada de Controller do padrâo MVC</strong></h3>

<p>A camada de Controller é responsável por orquestrar toda requisição , recebe requisições e retorna dados . Ela direciona as requisições para os serviços adequados e coordenam o fluxo de dados entre a interface do usuário e a lógica de negócios.</p>
  
~~~java
  @RestController
  @RequestMapping("/user")
  public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create( @Valid @RequestBody User user) {
        User userCreated = userService.save(user);

        return ResponseEntity.status(201).body(userCreated);
    }
~~~

* A anotação <code>@RestController</code> será um controller do sistema , a partir de requisições Rest.
* A anotação <code>@RequestMapping()</code> serve para mapear a **URL** da requisição **HTTP** , dentro do parentese você passa o caminho . ex : ("/user")
* A anotação <code><strong>@PostMapping</strong></code> esté é uma dos verbos HTTP o <code>POST</code> geralmente é utilizado para enviar dados para serem gravados , para atribui valores a entidade .
* A anotação <code>@ResponseStatus()</code> esse é o retorno da requisição que foi enviada ,dentro do parentese você passa o código HTTP de resposata . ex(HttpStatus.CREATED)
* A anotação <code>@RequestBody</code> Retorna o "corpo" da nossa requisição , geralmente o Json que estamos levando como parâmetro no body.
* No return é passado o status(201) os codigos 200 - 299 são utilizados para respostas bem sucedidas , o 201 em especifico é o codigo para um novo recurso criado . E no Body(corpo) da requisição irá mostrar a estrutura da entidade passada lá no **service**.
 
