import { TestBed } from '@angular/core/testing';

import { AnnaShopFormService } from './anna-shop-form.service';

describe('AnnaShopFormService', () => {
  let service: AnnaShopFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnnaShopFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
