/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbnzTestModule } from '../../../test.module';
import { DrugComponent } from '../../../../../../main/webapp/app/entities/drug/drug.component';
import { DrugService } from '../../../../../../main/webapp/app/entities/drug/drug.service';
import { Drug } from '../../../../../../main/webapp/app/entities/drug/drug.model';

describe('Component Tests', () => {

    describe('Drug Management Component', () => {
        let comp: DrugComponent;
        let fixture: ComponentFixture<DrugComponent>;
        let service: DrugService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DrugComponent],
                providers: [
                    DrugService
                ]
            })
            .overrideTemplate(DrugComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Drug(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.drugs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
