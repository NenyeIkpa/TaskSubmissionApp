import React, { useEffect, useState } from 'react';
import { useLocalState } from '../../util/useLocalStorage';
import { Link, useNavigate } from 'react-router-dom';
import api from '../../service/apiservice';
import { Button, Card, Col, Container, Row } from 'react-bootstrap';

const Dashboard = () => {
    
  const [token] = useLocalState("", "token");
  const [tasks, setTasks] = useState(null); 

  useEffect(() => {
    api('api/v1/tasks', 'GET', token)
    .then((tasks) => {
      console.log(tasks);
      setTasks(tasks)
    });

  }, [])


  const createNewTask = () => {
    api('api/v1/tasks', 'POST', token)
    .then(task => {
      console.log(task);
      window.location.href = `/tasks/${task.id}`;
    })
  }
    
    return (
    <>
       <div className='mt-3'>
    <Button variant='success' style={{margin: '2em'}} onClick={createNewTask}>Submit New Task</Button>
   </div>
    <div  className='d-grid gap-3' style={{gridTemplateColumns: 'repeat(auto-fill, 18em'}}>
    {/* <Row> */}
      {tasks ? 
      tasks.map((task) => (
        // <Col>
      // <div key={task.id}>
         <Card style={{ width: '20rem', margin: '2em' }}>
      <Card.Body>
        <Card.Title>Task #{task?.id}</Card.Title>
        <Card.Subtitle className="mb-2 text-muted">{task?.status}</Card.Subtitle>
        <Card.Text style={{marginTop: '2em'}}>
          <p><b>Github URL</b>: {task?.githubUrl}</p>
          <p><b>Branch</b>: {task?.branch}</p>
        </Card.Text>
         <Link to={`/tasks/${task.id}`} state={{task: task}}><Button>Edit</Button></Link>
      </Card.Body>
    </Card>
        // </div>
        // </Col>
        )) : (<div></div>)}
   {/* </Row> */}
   </div>
    </>
    );
};

export default Dashboard;