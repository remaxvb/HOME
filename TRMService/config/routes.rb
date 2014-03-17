TRMService::Application.routes.draw do
  namespace :api do
    devise_for :users, :skip => [:registrations, :password]
    devise_scope :user do
      post "users/sign_up", :to => 'registrations#create'
      post "users/sign_in", :to => 'sessions#create'
      delete "users/sign_out", :to => 'sessions#destroy'
      post "users/changepassword", :to => 'users#changepassword'
    end
    #TOUR routes
    post "tours/create", :to => 'tours#create'
    post "tours/edit", :to => 'tours#edit'
    post "tours/info", :to => 'tours#info'
    post "tours", :to => 'tours#gettours'
    post "tours/update", :to => 'tours#update'
    delete "tours/delete", :to => 'tours#detroy'
    get "users/user_info", :to => 'users#info'

    #MEMBER routes
    post "members/create", :to => 'members#create'
    post "members/edit", :to => 'members#edit'
    post "members/info", :to => 'members#info'
    post "members/update", :to => 'members#update'
    delete "members/delete", :to => 'members#detroy'

    #Invitation routes
    post "invitations/create", :to => 'invitations#create'
    post "invitations/edit", :to => 'invitations#edit'
    post "invitations/info", :to => 'invitations#info'
    post "invitations/update", :to => 'invitations#update'
    delete "invitations/delete", :to => 'invitations#detroy'


  end
  # The priority is based upon order of creation: first created -> highest priority.
  # See how all your routes lay out with "rake routes".

  # You can have the root of your site routed with "root"
  # root 'welcome#index'

  # Example of regular route:
  #   get 'products/:id' => 'catalog#view'

  # Example of named route that can be invoked with purchase_url(id: product.id)
  #   get 'products/:id/purchase' => 'catalog#purchase', as: :purchase

  # Example resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Example resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Example resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Example resource route with more complex sub-resources:
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', on: :collection
  #     end
  #   end

  # Example resource route with concerns:
  #   concern :toggleable do
  #     post 'toggle'
  #   end
  #   resources :posts, concerns: :toggleable
  #   resources :photos, concerns: :toggleable

  # Example resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end
end
