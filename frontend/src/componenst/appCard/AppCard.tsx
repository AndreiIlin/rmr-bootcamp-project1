import React, { FC } from 'react';
import { Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../utils/routes';

interface AppCardProps {
  id: string;
  appName: string;
  iconImage: string;
  description?: string;
}

const AppCard: FC<AppCardProps> = ({ appName, iconImage, id, description }) => {
  const navigate = useNavigate();
  const clickHandler = (): void => {
    navigate(routes.pages.appPagePath(id));
  };
  return (
    <Card className="col-6 col-sm-3 col-lg-2 bg-transparent border-0 text-center">
      <Card.Body>
        <Card.Img
          src={iconImage}
          alt={appName}
          title={description}
          className="img-custom"
          onClick={clickHandler}
        />
        <Card.Text className="text-light text-truncate mt-3 overflow-">{appName}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default AppCard;
