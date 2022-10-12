export interface App {
  id: string;
  ownerId: string;
  appName: string;
  appDescription: string;
  featurePrice: number;
  bugPrice: number;
  available: boolean;
  iconImage: string;
  downloadLink: string;
}
