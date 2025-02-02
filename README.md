# <div align="center">Oppo API V1.0.1</div>
<strong>ESTE PROJETO FOI DESCONTINUADO HÁ UM TEMPO</strong>
## <div align="center">Gestão Escolar Completa e Efetiva</div>

### <h2>Descrição</h2>
A **Oppo API** é uma solução voltada para a gestão de escolas de pequeno porte, que não podem gastar muito para sua gestão, permitindo o controle de turmas, alunos, professores e matérias. A seguir, estão descritas as funcionalidades já implementadas e as necessidades que o sistema supre.

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
```
<div align="center"> <h2>Desenvolvido por <a href="https://www.linkedin.com/in/erickson-augusto/">Erickson Dias</a></h2> </div> 

<svg width="30px" height="30px" viewBox="0 0 192.756 192.756" xmlns="http://www.w3.org/2000/svg">

<g fill-rule="evenodd" clip-rule="evenodd">

<path fill="#ffffff" d="M0 0h192.756v192.756H0V0z"/>

<path d="M80.372 101.729s-4.604 2.679 3.28 3.584c9.554 1.091 14.434.934 24.959-1.057 0 0 2.771 1.735 6.639 3.236-23.601 10.113-53.413-.585-34.878-5.763zM77.487 88.532s-5.165 3.823 2.726 4.639c10.206 1.054 18.262 1.14 32.211-1.544 0 0 1.926 1.955 4.957 3.023-28.531 8.345-60.307.657-39.894-6.118z" fill="#3174b9"/>

<path d="M101.797 66.143c5.818 6.697-1.525 12.72-1.525 12.72s14.766-7.621 7.984-17.168c-6.332-8.899-11.189-13.32 15.102-28.566-.001-.001-41.27 10.303-21.561 33.014z" fill="#ca3132"/>

<path d="M133.01 111.491s3.408 2.81-3.754 4.983c-13.619 4.125-56.694 5.369-68.659.164-4.298-1.872 3.766-4.467 6.303-5.015 2.646-.572 4.156-.468 4.156-.468-4.783-3.368-30.916 6.615-13.272 9.479 48.112 7.801 87.704-3.512 75.226-9.143zM82.587 74.857s-21.908 5.205-7.757 7.097c5.977.799 17.883.615 28.982-.316 9.068-.761 18.17-2.389 18.17-2.389s-3.195 1.371-5.51 2.949c-22.251 5.853-65.229 3.127-52.855-2.856 10.462-5.061 18.97-4.485 18.97-4.485zM121.891 96.824c22.617-11.75 12.16-23.044 4.859-21.522-1.785.373-2.586.695-2.586.695s.666-1.042 1.932-1.49c14.441-5.075 25.545 14.972-4.656 22.911-.001 0 .347-.314.451-.594z" fill="#3174b9"/>

<path d="M108.256 8.504s12.523 12.531-11.881 31.794c-19.571 15.458-4.462 24.269-.006 34.34-11.426-10.307-19.807-19.382-14.185-27.826 8.254-12.395 31.125-18.406 26.072-38.308z" fill="#ca3132"/>

<path d="M84.812 128.674c21.706 1.388 55.045-.771 55.836-11.044 0 0-1.518 3.894-17.941 6.983-18.529 3.488-41.386 3.082-54.938.845 0 0 2.777 2.298 17.043 3.216z" fill="#3174b9"/>

<path d="M139.645 147.096h-.66v-.37h1.781v.37h-.66v1.848h-.461v-1.848zm3.554.092h-.008l-.656 1.755h-.301l-.652-1.755h-.008v1.755h-.438v-2.218h.643l.604 1.569.604-1.569h.637v2.218h-.424v-1.755h-.001zM81.255 167.921c-2.047 1.774-4.211 2.772-6.154 2.772-2.768 0-4.27-1.663-4.27-4.324 0-2.881 1.608-4.989 8.044-4.989h2.379v6.541h.001zm5.65 6.374v-19.732c0-5.043-2.876-8.371-9.809-8.371-4.045 0-7.591.999-10.474 2.272l.83 3.495c2.271-.834 5.207-1.607 8.089-1.607 3.994 0 5.713 1.607 5.713 4.934v2.495h-1.996c-9.702 0-14.08 3.764-14.08 9.423 0 4.876 2.885 7.648 8.316 7.648 3.491 0 6.099-1.441 8.534-3.55l.443 2.993h4.434zM105.762 174.295h-7.045l-8.483-27.601h6.154l5.265 16.961 1.172 5.096c2.656-7.371 4.541-14.854 5.484-22.057h5.984c-1.602 9.088-4.488 19.066-8.531 27.601zM132.799 167.921c-2.053 1.774-4.217 2.772-6.156 2.772-2.768 0-4.268-1.663-4.268-4.324 0-2.881 1.609-4.989 8.041-4.989h2.383v6.541zm5.652 6.374v-19.732c0-5.043-2.885-8.371-9.811-8.371-4.049 0-7.594.999-10.477 2.272l.83 3.495c2.271-.834 5.213-1.607 8.096-1.607 3.988 0 5.709 1.607 5.709 4.934v2.495h-1.996c-9.703 0-14.078 3.764-14.078 9.423 0 4.876 2.879 7.648 8.311 7.648 3.494 0 6.098-1.441 8.539-3.55l.445 2.993h4.432zM58.983 178.985c-1.61 2.353-4.214 4.216-7.061 5.267l-2.79-3.286c2.169-1.113 4.027-2.91 4.892-4.582.745-1.49 1.056-3.406 1.056-7.992v-31.515h6.005v31.08c0 6.134-.49 8.613-2.102 11.028z" fill="#ca3132"/>

</g>

</svg>

<svg width="30px" height="30px" viewBox="0 0 256 256" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" preserveAspectRatio="xMidYMid">
    <g>
        <path d="M38.9437824,35.879008 C89.5234256,-13.1200214 170.398168,-11.8028432 219.397197,39.0402357 C224.929346,31.6640377 229.671187,23.4975328 233.095851,15.0675923 C249.165425,64.0666217 258.912543,105.162582 255.224444,137.038295 C253.380395,163.90873 242.842969,189.725423 225.456217,210.273403 C180.145286,264.014274 99.53398,270.863601 45.7931091,225.55267 L45.7931091,225.55267 L44.765,224.638 L44.7103323,224.601984 C44.5420247,224.484832 44.376007,224.362668 44.2124952,224.235492 C43.7219599,223.853965 43.2765312,223.438607 42.8762093,222.995252 L42.732,222.831 L41.0512675,221.3377 C39.4121124,219.93271 37.7729573,218.52772 36.3188215,216.93771 L35.7825547,216.332423 C-13.2164747,165.752779 -11.6358609,84.8780374 38.9437824,35.879008 Z M57.9111486,207.375611 C53.169307,203.687512 46.3199803,204.214383 42.6318814,208.956225 C39.3888978,213.125775 39.4048731,218.924805 42.6798072,222.771269 L42.732,222.831 L44.765,224.638 L44.9644841,224.773953 C49.5691585,227.80174 55.7644273,227.175885 59.2982065,222.896387 L59.4917624,222.654878 C63.1798614,217.913037 62.3895545,211.06371 57.9111486,207.375611 Z M231.778672,28.2393744 C218.60689,55.9001168 185.940871,76.9749681 157.753257,83.5608592 C131.146257,89.8833146 107.963921,84.6146018 83.4644059,94.0982849 C27.6160498,115.436572 28.6697923,181.822354 59.2283268,196.838185 L59.2283268,196.838185 L61.0723763,197.891928 C61.0723763,197.891928 83.1456487,193.50309 104.973663,187.707242 L106.843514,187.207079 C115.561826,184.857554 124.138869,182.296538 131.146257,179.714869 C167.500376,166.279651 207.542593,133.08676 220.714375,94.6251562 C213.865049,134.667374 179.35498,173.392413 144.84491,191.042601 C126.404416,200.526284 112.178891,202.633769 81.883792,213.171195 C78.195693,214.488373 75.297901,215.805551 75.297901,215.805551 C75.6675607,215.754564 76.0372203,215.70481 76.4060145,215.65629 L77.1421925,215.560893 L77.1421925,215.560893 L77.8745239,215.468787 C84.5652297,214.639554 90.5771682,214.224938 90.5771682,214.224938 C133.517178,212.117452 200.956702,226.342977 232.305544,184.45671 C264.444692,141.780136 246.531068,72.7599979 231.778672,28.2393744 Z" fill="#6DB33F">

</path>
        <path d="M57.9111486,207.375611 C62.3895545,211.06371 63.1798614,217.913037 59.4917624,222.654878 C55.8036635,227.39672 48.9543368,227.923591 44.2124952,224.235492 C39.4706537,220.547393 38.9437824,213.698066 42.6318814,208.956225 C46.3199803,204.214383 53.169307,203.687512 57.9111486,207.375611 Z M231.778672,28.2393744 C246.531068,72.7599979 264.444692,141.780136 232.305544,184.45671 C200.956702,226.342977 133.517178,212.117452 90.5771682,214.224938 C90.5771682,214.224938 84.5652297,214.639554 77.8745239,215.468787 L77.1421925,215.560893 C76.5300999,215.63902 75.9140004,215.720572 75.297901,215.805551 C75.297901,215.805551 78.195693,214.488373 81.883792,213.171195 C112.178891,202.633769 126.404416,200.526284 144.84491,191.042601 C179.35498,173.392413 213.865049,134.667374 220.714375,94.6251562 C207.542593,133.08676 167.500376,166.279651 131.146257,179.714869 C106.119871,188.935116 61.0723763,197.891928 61.0723763,197.891928 L59.2283268,196.838185 C28.6697923,181.822354 27.6160498,115.436572 83.4644059,94.0982849 C107.963921,84.6146018 131.146257,89.8833146 157.753257,83.5608592 C185.940871,76.9749681 218.60689,55.9001168 231.778672,28.2393744 Z" fill="#FFFFFF">

</path>
    </g>
</svg>
