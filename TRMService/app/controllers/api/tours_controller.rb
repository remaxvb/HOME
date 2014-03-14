class Api::ToursController < ApplicationController
  #Skip authenication
  skip_before_action :verify_authenticity_token

  # POST /api/tours/create
  def create
    tour = Tour.new(tour_params)
    if tour.save
      render :json => {:success => true}
    else
      render :json => {:success => false, :message => tour.errors}
    end
  end

  #POST /api/tours/edit
  def edit

  end

  #POST /api/tours/info
  def info

  end

  #POST /api/tours/update
  def update

  end

  #DELETE /api/tours/delete
  def destroy

  end

  private
  def tour_params
    params.require(:user).permit(:title, :destination, :description, :adult_price,
                                 :child_price, :depature_date, :return_date, :id_manager,
                                 :max_member, :currency) if params[:user]
  end
end
