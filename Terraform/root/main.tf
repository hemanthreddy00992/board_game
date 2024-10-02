module "vpc" {
    source = "../modules/vpc"
    vpc_cidr = var.vpc_cidr
    pub_cidr = var.pub_cidr
}

module "SG" {
  source = "../modules/SG"
  vpc_id = module.vpc.vpc_id
}

module "EC2" {
  source = "../modules/EC2"
  instance_type = var.instance_type
  sg_id = module.SG.sg_id
  pub_id = module.vpc.pub_id
}
