import { TestBed } from '@angular/core/testing';

import { CanActivateRouteService } from './can-activate-route.service';

describe('CanActivateRouteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CanActivateRouteService = TestBed.get(CanActivateRouteService);
    expect(service).toBeTruthy();
  });
});
