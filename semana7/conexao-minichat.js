const net = require('net');

const PORT = 12345;
const HOST = '10.104.12.40';

const client = new net.Socket();

client.connect(PORT, HOST, () => {
    console.log('Conectado com sucesso');
    client.write('Oi\n');
});

client.on('data', (data) => {
    console.log('Server respondeu: ' + data.toString());
    
    client.end();
});

client.on('close', () => {
    console.log('Conexão encerrada.');
});

client.on('error', (err) => {
    console.error('Erro na conexão:', err.message);
});

