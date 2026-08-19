# Aula 1

Prof: Alexandre Zamberlan (Alexz@ufn.edu.br)

Nota de aula vale 20% (esse arquivo aqui), participação 20%, prova/trabalho prático 60%.

Projeto em código segue mais ou menos essa estrutura: Controller, Model, Service, Communication.

## Sistemas distribuídos

Várias máquinas trabalhando junto, trocando informação pela rede. 4 pilares pra entender o assunto:
- comunicação
- arquitetura
- processamento (concorrente x paralelo)
- cluster x grid

## Comunicação

Unicast = manda pra um só, multicast = manda pra um grupo, broadcast = manda pra todo mundo.

Comunicação bloqueante: tem quem escreve/envia (writer/sender) e quem lê/recebe (reader/receiver).

Rede: modelo TCP/IP é aplicação -> transporte -> interface -> rede. Socket é o ponto de conexão entre duas máquinas. Porta lógica identifica o serviço dentro da máquina. Máscara/classe de rede define o domínio.

## Arquitetura

Cliente-servidor: um lado serve, o outro consome.
P2P (ponto a ponto): todo mundo é igual, serve e consome ao mesmo tempo.

## Threads

Thread é tipo um mini processo dentro de um processo, serve pra rodar coisa ao mesmo tempo (concomitante).

Estados: execução, pronto/finalizado, espera, parado, dormindo, cancelado.

Com memória compartilhada -> precisa de sincronismo (monitor, semáforo), fica mais complexo, responsabilidade do programador. Em Java usa a interface Runnable.
Sem memória compartilhada -> mais simples, não precisa sincronizar. Em Java usa a classe Thread direto.

Pra que serve thread: rodar tarefa simultânea e, em SD, desbloquear a comunicação que é bloqueante.

## Concorrente x paralelo

Concorrente (concomitante): 1 CPU só ficando alternando rápido entre as tarefas, dá a impressão de que é ao mesmo tempo mas não é.

Paralelo: várias tarefas rodando de fato ao mesmo tempo, em várias unidades de processamento.
- fortemente acoplado: mesma máquina (ex: CPU multicore, GPU), memória compartilhada
- fracamente acoplado: várias máquinas em rede (cluster), cada uma com a sua memória

## Processo x thread

Processo: isolado, memória própria, comunicação via IPC (mais custosa), custo de criação alto, mais robusto (se um cai não derruba os outros).

Thread: não é isolada, compartilha a memória do processo, comunicação direta e rápida, custo baixo de criar, menos robusta (se uma cai pode derrubar o processo inteiro).

Exemplo servidor web: com processo, cada cliente é atendido por um processo isolado; com thread, um processo só atende vários clientes por dentro.

Em SD: processo pode estar em máquina diferente; thread fica dentro do processo, aproveitando a máquina local.

---

# Aula 2

Pra que serve SD: compartilhar recurso, simples ou complexo.
Como funciona na prática: trocando dado (bytes) entre os nós.

Thread de novo: mini processo dentro do processo, criado pra rodar rotina de forma concomitante.

Onde thread encaixa melhor: mineração, tratamento de dado, análise de dado, rotina que não tem seção crítica.

Tipos:
- sem memória compartilhada: sem seção crítica, mais simples de mexer
- com memória compartilhada: tem seção crítica, precisa sincronizar, mais complexo (pode dar conflito no acesso ao dado)

Processo cria thread de formas diferentes:
- Thread (classe) -> geralmente quando não tem memória compartilhada
- Runnable (interface) -> geralmente quando tem memória compartilhada

# Aula 3 - identificação de threads

Dá pra identificar/acompanhar uma thread mesmo sem ela compartilhar memória:
- ID: número único dela dentro do processo
- nome: ajuda a reconhecer qual é
- thread atual: qual tá rodando naquele momento

| linguagem | thread atual | nome | id |
|---|---|---|---|
| java | `Thread.currentThread()` | `.getName()` | `.getId()` |
| c# | `Thread.CurrentThread` | `.Name` | `.ManagedThreadId` |
| python | `threading.current_thread()` | `.name` | `threading.get_ident()` |
