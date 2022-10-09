import React, { FC } from 'react';
import { useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import logo from '../../assets/logo.png';

const Nav: FC = () => {
  const auth = useAppSelector(selectors.userAuth);
  return (
    <div className='d-flex justify-content-between align-items-center nav'>
      <div className='logo d-flex align-items-center'>
        <img src={logo} alt='TrueStore' />
        <div className='logo-text'>
          <span>true</span>store
        </div>
      </div>
      {auth ? <div className='user'></div> : null}
    </div>
  );
};

export default Nav;
