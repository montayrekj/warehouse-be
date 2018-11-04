<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id="page-wrapper">
     <div class="container-fluid">
          <div class="row bg-title">
               <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">Employee List</h4> 
               </div>
          </div>
          
          <div class="row">
               <div class="col-md-9 col-lg-9 col-sm-7">
                    <div class="panel panel-info">
                         <div class="panel-heading"> 
                              Product List
                         </div>
                         <div class="panel-wrapper collapse in">
                              <div class="panel-body">
                                   <div class="table-responsive">
                                        <!-- CSFR token for ajax call -->
                                        <input type="hidden" name="_token" val="{{ csrf_token() }}"/>
                                        <table class="table product-overview">
                                             <thead>
                                                  <tr>
                                                       <th>Image</th>
                                                       <th>Product info</th>
                                                       <th class="text-center">Category</th>
                                                       <th class="text-center">Action</th>
                                                  </tr>
                                             </thead>

                                             <tbody>

                                                  <tr>
                                                       <td width="13%">
                                                            <img src="" alt="Sample image" width="80">
                                                       </td>
                                                       <td width="35%">
                                                            <h5>Asda</h5>
                                                            <p>asda</p>
                                                       </td>
                                                       <td align="center" width="20%">aaaaaaaaaaa</td>
                                                       <td align="center" width="10%">
                                                            <a href="/tango/products/edit/1" class="btn btn-info btn-outline btn-circle">
                                                                 <i class="fa fa-pencil"></i>
                                                            </a>
                                                      </td>
                                                  </tr>

                                             </tbody>
                                        </table>
                                   </div>
                              </div>
                         </div>
                    </div>
               </div>

               <div class="col-md-3 col-lg-3 col-sm-5">
                   <div class="white-box">
                       <h3 class="box-title" style="margin-bottom: 0px">All Products
                           <span class="pull-right" id="count">
                               3
                           </span>
                       </h3>
                   </div>  
         
                    <div class="white-box">
                         <h3 class="box-title">New Product</h3>
                         <hr>
                         <a href="/tango/products/add" class="btn btn-info btn-block">Add New Product</a>
                    </div>
               </div>
        </div>
     </div>
</div> 