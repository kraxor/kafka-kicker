import { useState, useEffect } from 'react';
import { ConnectionResourceApi, Connection } from '@kafka-kicker/typescript-axios';

function ConnectionList() {
    const [connections, setConnections] = useState<Connection[]>([]);

    useEffect(() => {
        const apiClient = new ConnectionResourceApi();

        apiClient.connectionGet().then(response => {
            const connections = response.data;
            setConnections(() => connections)
        }).catch(error => console.error(error));
    }, []);

    return (
        <div>
            <h1>Connections ({connections.length})</h1>
            <ul>
                {connections.map((connection, index) => (
                    <li key={index}>
                        {JSON.stringify(connection)}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ConnectionList;
