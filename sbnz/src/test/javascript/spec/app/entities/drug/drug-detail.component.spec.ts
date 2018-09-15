/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SbnzTestModule } from '../../../test.module';
import { DrugDetailComponent } from '../../../../../../main/webapp/app/entities/drug/drug-detail.component';
import { DrugService } from '../../../../../../main/webapp/app/entities/drug/drug.service';
import { Drug } from '../../../../../../main/webapp/app/entities/drug/drug.model';

describe('Component Tests', () => {

    describe('Drug Management Detail Component', () => {
        let comp: DrugDetailComponent;
        let fixture: ComponentFixture<DrugDetailComponent>;
        let service: DrugService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DrugDetailComponent],
                providers: [
                    DrugService
                ]
            })
            .overrideTemplate(DrugDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Drug(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.drug).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
