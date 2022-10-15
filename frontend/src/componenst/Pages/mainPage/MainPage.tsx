import React, { FC } from 'react';
import { Button, Form, FormControl } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { App } from '../../../models/services/app';
import { useGetAppsQuery } from '../../../store/api/appsApiSlice/appsApiSlice';
import ShortAppCard from '../shortAppCard';

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

const AppPage: FC = () => {
  const { t } = useTranslation();
  const { data } = useGetAppsQuery({
    page: 0,
    size: 10,
    filter: '',
  });
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
      <div className='app-container'>
        {data?.length === 0 ? (
          <div>{t('appsFields.notFoundApps')}</div>
        ) : (
          data?.map((item: App) => (
            <ShortAppCard
              key={item.id}
              iconImage={item.iconImage}
              appName={item.appName}
              id={item.id}
            />
          ))
        )}
      </div>
    </main>
  );
};

export default AppPage;
