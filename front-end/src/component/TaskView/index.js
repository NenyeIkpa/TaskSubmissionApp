import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useLocalState } from '../../util/useLocalStorage';
import api from '../../service/apiservice';

const TaskView = () => {
   
    // const taskId = window.location.href.split('/tasks/')[1];
    const {state} = useLocation();
    // const task = state.task;
   
    const [token] = useLocalState("", "token");
    const [task, setTask] = useState({
        githubUrl: '',
        branch: ''
    }); 

    const handleChange = (name, value) => {
        setTask(prev => ({...prev, [name]: value}));
    }
    console.log(task);

    const updateTask = () => {
      api(`/api/v1/tasks/${task?.id}`, 'PUT', token, task)
      .then((task) => {
        setTask(task)
      });
    }

  useEffect(() => {
    api(`/api/v1/tasks/${state.task.id}`, 'GET', token)
    .then((taskData) => {
      //to do: refactor backend to return empty string?  find another workaround
      if (taskData.githubUrl === null) taskData.githubUrl = '';
      if (taskData.branch === null) taskData.branch = '';
      setTask(taskData)
    });

  }, [])
  
    return (
        <div>
           <h1>Task {task?.id}</h1>
           <h2>Status: {task?.status}</h2>
           <h3>Github URL:<input type='url' id='githubUrl' value={task?.githubUrl} onChange={(e) => handleChange('githubUrl', e.target.value)} /></h3>
           <h3>Branch: <input type='text' id='branch' value={task?.branch} onChange={(e) => handleChange('branch', e.target.value)}/></h3>
           <button onClick={updateTask}>Submit Task</button>
        </div>
    );
}

export default TaskView;