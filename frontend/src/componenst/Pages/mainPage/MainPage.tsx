import React from 'react';
import { Form, FormControl, Button } from 'react-bootstrap';
const categories = [
  'Государственные',
  'Еда и напитки',
  'Здоровье и спорт',
  'Инструменты',
  'Медицина',
  'Новости',
  'Образование',
  'Покупки',
  'Развлечения',
  'Финансы',
];

const AppPage = () => {
  return (
    <main className='app d-flex gap-3'>
      <aside className='left-aside d-flex gap-3 flex-column'>
        <Form className='searchForm'>
          <FormControl type='search' placeholder='Поиск' />
        </Form>
        <Button type='button' className='d-block w-100 mr-3' variant='warning'>
          Все предложения
        </Button>
        <section className='category mt-5'>
          <h4>Категории</h4>
          <ul>
            {categories.map((category, index) => (
              <li key={index}>{category}</li>
            ))}
          </ul>
        </section>
      </aside>
      <div className='app-container'></div>
    </main>
  );
};

export default AppPage;
