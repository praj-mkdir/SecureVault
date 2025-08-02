module "s3_bucket" {
  source      = "../../modules/s3"
  bucket_name = "securevaultprajv1"
  region      = "us-east-1"
  environment = "prod"
}
