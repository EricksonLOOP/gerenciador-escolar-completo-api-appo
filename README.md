# <div align="center">Oppo API</div>

## <div align="center">Gestão Escolar Completa e Efetiva</div>

### <h2>Descrição</h2>
A **Oppo API** é uma solução voltada para a gestão de escolas, permitindo o controle de turmas, alunos, professores e matérias. A seguir, estão descritas as funcionalidades já implementadas e as necessidades que o sistema supre.

---

### <h2>Necessidades Supridas</h2>

#### <h3>1. Gestão de Escolas</h3>
- **Criar e Atualizar Escolas**:  
  A API permite a criação de novas escolas e a atualização das informações de escolas existentes.  
  <b>Necessidade suprida:</b> <i>Cadastro e manutenção de dados institucionais</i>.

#### <h3>2. Gestão de Alunos na Escola</h3>
- **Adicionar e Remover Alunos**:  
  As escolas podem matricular ou remover alunos de seu banco de dados, facilitando o controle de matrícula.
  
- **Listar Alunos da Escola**:  
  Visualização da lista de alunos matriculados em uma escola específica.  
  <b>Necessidade suprida:</b> <i>Gerenciamento de matrículas e controle de alunos ativos</i>.

#### <h3>3. Gestão de Professores na Escola</h3>
- **Adicionar e Remover Professores**:  
  As escolas podem adicionar novos professores ao quadro docente ou removê-los quando necessário.
  
- **Listar Professores da Escola**:  
  Visualização da lista de professores atuantes em uma escola.  
  <b>Necessidade suprida:</b> <i>Gerenciamento de recursos humanos (professores)</i>.

#### <h3>4. Gestão de Turmas</h3>
- **Criar e Deletar Turmas**:  
  Possibilidade de criação de novas turmas e exclusão de turmas antigas.
  
- **Listar Turmas**:  
  A API permite visualizar todas as turmas criadas em uma escola específica.
  
- **Adicionar e Remover Matérias nas Turmas**:  
  Flexibilidade para adicionar ou remover matérias específicas em cada turma.
  
- **Matricular e Mover Alunos entre Turmas**:  
  Alunos podem ser matriculados em turmas ou transferidos para outras turmas conforme necessário.  
  <b>Necessidade suprida:</b> <i>Organização e gerenciamento de turmas, alocação de alunos e matérias</i>.

#### <h3>5. Gestão de Matérias</h3>
- **Criar e Deletar Matérias**:  
  As escolas podem gerenciar o currículo, criando novas matérias ou removendo aquelas que não fazem mais parte do programa.
  
- **Adicionar Professor Responsável por Matérias**:  
  Função para alocar professores às matérias, garantindo que cada disciplina tenha seu docente.
  
- **Listar Matérias da Escola**:  
  A API permite visualizar todas as matérias disponíveis na escola.  
  <b>Necessidade suprida:</b> <i>Gerenciamento do currículo escolar e alocação de professores às matérias</i>.

---

### <h2>Rotas Disponíveis</h2>

#### <h3>Escolas</h3>
- **POST /escolas** - Criar uma nova escola
- **PUT /escolas/{id}** - Atualizar dados de uma escola existente

#### <h3>Alunos</h3>
- **POST /escolas/{idEscola}/alunos/{idAluno}** - Adicionar aluno em uma escola
- **DELETE /escolas/{idEscola}/alunos/{idAluno}** - Remover aluno de uma escola
- **GET /escolas/{idEscola}/alunos** - Listar alunos de uma escola

#### <h3>Professores</h3>
- **POST /escolas/{idEscola}/professores/{idProfessor}** - Adicionar professor em uma escola
- **DELETE /escolas/{idEscola}/professores/{idProfessor}** - Remover professor de uma escola
- **GET /escolas/{idEscola}/professores** - Listar professores de uma escola

#### <h3>Turmas</h3>
- **POST /escolas/{idEscola}/turmas** - Criar uma turma em uma escola
- **DELETE /escolas/{idEscola}/turmas/{idTurma}** - Deletar uma turma
- **GET /escolas/{idEscola}/turmas** - Listar turmas de uma escola
- **POST /escolas/{idEscola}/turmas/{idTurma}/materias** - Adicionar matéria em uma turma
- **DELETE /escolas/{idEscola}/turmas/{idTurma}/materias/{idMateria}** - Remover matéria de uma turma
- **POST /escolas/{idEscola}/turmas/{idTurma}/alunos/{idAluno}** - Matricular aluno em uma turma
- **PUT /escolas/{idEscola}/turmas/{idTurma}/alunos/{idAluno}** - Mover aluno de uma turma para outra

#### <h3>Matérias</h3>
- **POST /materias** - Criar uma nova matéria
- **DELETE /materias/{idMateria}** - Deletar uma matéria
- **GET /materias** - Listar todas as matérias
- **POST /materias/{idMateria}/professores/{idProfessor}** - Adicionar professor responsável por uma matéria

---

### <h2>Instalação e Uso</h2>
Clone o repositório e siga os passos abaixo para rodar o projeto localmente.

```bash
git clone https://github.com/usuario/repo-oppo.git
cd repo-oppo
<div align="center"> <h2>Desenvolvido por Erickson Dias</h2> </div> ```
