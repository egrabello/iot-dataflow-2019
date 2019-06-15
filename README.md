# iot-dataflow-2019
Implementação de um dataflow para pré-processamento de dados na borda da rede em um contexto de IoT

- br.ufrj.cos.iotlab2019.coap-client: Projeto Java que implementa um cliente CoAP para observar recursos expostos por um servidor CoAP;
- br.ufrj.cos.iotlab2019.coap-server: Projeto Java que implementa um servidor CoAP, expondo recursos de temperatura e humidade, que retornam leituras realizadas através de um sensor DHT11;
- dataflow.xml: Template Apache NiFi que implementa um processamento de dados "crus" de temperatura e humidade, efetuando agregações simples (contagem, máximos, mínimos, médias, etc.) com o intuito de reduzir o volume de dados a ser persistido em nuvem (Amazon S3);
- ingest.py: Script Python utilizado para simular a ingestão dos dados coletados ao longo de um período a uma frequência maior, para efeito de testes.
