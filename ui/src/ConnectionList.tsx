import { useState, useEffect } from 'react';
import { ConnectionResourceApi } from '@kafka-kicker/typescript-axios';

function ConnectionList() {
    const [connections, setConnections] = useState([]);

    useEffect(() => {
        const apiClient = new ConnectionResourceApi();

        apiClient.connectionGet().then(response => {
            const connections = response.data;
            setConnections(() => connections)
        }).catch(error => console.error(error));
    }, []);

    console.log(connections)

    return (
        <div>
            <h1>Connections</h1>
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
