import React, { FC, useState } from 'react';
import AppsContainer from '../../componenst/appsContainer';
import { useGetAppsQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const MainPage: FC = () => {
  const [filter, setFilter] = useState('');
  const [page, setPage] = useState<number>(0);
  const { data } = useGetAppsQuery({
    page,
    size: 12,
    filter,
  });

  return <AppsContainer data={data} setFilter={setFilter} page={page} setPage={setPage} />;
};

export default MainPage;
