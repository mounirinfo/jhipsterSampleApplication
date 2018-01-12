/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PriceDetailComponent } from '../../../../../../main/webapp/app/entities/price/price-detail.component';
import { PriceService } from '../../../../../../main/webapp/app/entities/price/price.service';
import { Price } from '../../../../../../main/webapp/app/entities/price/price.model';

describe('Component Tests', () => {

    describe('Price Management Detail Component', () => {
        let comp: PriceDetailComponent;
        let fixture: ComponentFixture<PriceDetailComponent>;
        let service: PriceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PriceDetailComponent],
                providers: [
                    PriceService
                ]
            })
            .overrideTemplate(PriceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Price(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.price).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
