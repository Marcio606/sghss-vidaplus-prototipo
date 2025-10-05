import React, { useEffect, useState } from 'react';

function App() {
  const [patients, setPatients] = useState([]);

  useEffect(() => {
    fetch('/api/patients') // Substitua pelo endpoint real
      .then(response => response.json())
      .then(data => setPatients(data))
      .catch(error => console.error('Error fetching patients:', error));
  }, []);

  return (
    <div>
      <h1>Lista de Pacientes</h1>
      <ul>
        {patients.map(patient => (
          <li key={patient.id}>{patient.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;