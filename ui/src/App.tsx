import {useEffect, useState} from 'react';
import './App.css';
import {Connection, ConnectionResourceApi} from '@kafka-kicker/typescript-axios'

const App = () => {
    const [connections, setConnections] = useState<Connection[]>([])

    const api = new ConnectionResourceApi()

    useEffect(() => {
        const listAll = async () => {
            console.log("doing it")
            const result = await api.connectionGet()
            console.log(result)
            console.log("done it")
        }

        listAll().then(r => console.log("all done"))
    }, [])

    return (<div>connection count: ${connections.length}</div>);
}

export default App;
