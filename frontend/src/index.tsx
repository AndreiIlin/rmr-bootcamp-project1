import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import init from './init';

const app = async () => {
  const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
  const vdom = await init();
  root.render(vdom);
};

app();
