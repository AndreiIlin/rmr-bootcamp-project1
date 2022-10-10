import React, { FC } from 'react';
import { useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';

const Nav: FC = () => {
  const auth = useAppSelector(selectors.userAuth);
  return (
    <div className='d-flex justify-content-between align-items-center nav'>
      <div className='logo'>
        <span>true</span>store
      </div>
      {auth ? <div className='user'></div> : null}
    </div>
  );
};

export default Nav;
