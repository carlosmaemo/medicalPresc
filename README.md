# Sistema de Gestão de Prescrição Médica

Este sistema foi desenvolvido para facilitar a prescrição médica, controle de medicamentos e entrega de receitas em uma clínica. O processo envolve três etapas principais: a prescrição do médico, a confirmação e entrega dos medicamentos pela farmácia, e a emissão do recibo para o paciente. O sistema também permite que o paciente acesse sua prescrição diretamente de casa.

## Funcionalidades

### 1. Prescrição Médica
- O médico pode registrar a prescrição de medicamentos para o paciente.
- Cada prescrição contém informações detalhadas sobre o medicamento (nome, dosagem, quantidade, etc.).
- O sistema armazena o histórico de prescrições de cada paciente.

### 2. Confirmação e Entrega pela Farmácia
- A farmácia pode visualizar a prescrição do médico.
- A farmácia confirma a disponibilidade dos medicamentos e realiza a entrega dos itens prescritos.
- Após a entrega, a farmácia gera um recibo detalhado com os medicamentos entregues e os valores.

### 3. Acesso ao Paciente
- O paciente tem acesso à sua prescrição diretamente de casa, podendo visualizar os medicamentos prescritos, quantidades e instruções.
- O paciente pode consultar o histórico de suas prescrições anteriores.
  
## Tecnologias Utilizadas

- **Frontend**: HTML, CSS
- **Backend**: Java
- **Banco de Dados**: SQLite (para armazenamento de prescrições, pacientes, e registros de entrega)
  
## Instalação

### 1. Clonando o Repositório

```bash
git clone https://github.com/carlosmaemo/medicalPresc.git
cd medicalPresc
