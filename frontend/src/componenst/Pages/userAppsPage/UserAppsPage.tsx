import React from 'react';
import { Container } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { App } from '../../../models/services/app';
import { useGetUserAppsQuery } from '../../../store/api/appsApiSlice/appsApiSlice';
import ShortAppCard from '../shortAppCard';

const UserAppsPage = () => {
  const { t } = useTranslation();
  const { data } = useGetUserAppsQuery({
    page: 0,
    size: 10,
    filter: '',
  });
  return (
    <Container className='d-flex gap-3 mt-3'>
      {data?.length === 0 ? (
        <div>{t('appsFields.notFoundUsersApps')}</div>
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
    </Container>
  );
};

export default UserAppsPage;
